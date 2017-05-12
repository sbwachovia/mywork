import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";

import { Message } from 'primeng/primeng';

import { DeviceAppService, DeviceSearch, LineDevice, DeviceRequest } from './../shared/services/device/device-app.service';
import {DeviceRootObject, ChildProducts} from './../shared/models/device-app-model';
import { StateCacheService } from "../shared/services/data-interaction/state-cache.service";
import { ErrorHandlingService } from "../shared/services/error-handling/error-handling.service";
import {MenuItem} from "../shared/components/tab-menu/tab-menu.component";
import {LineSummaryList} from './../shared/models/overview-app-model';
import {SpinnerUtils} from "../shared/utils/spinner-utils";
import {InteractionService} from "../shared/services/data-interaction/interaction-service";


@Component({
    selector: 'device-app',
    templateUrl: './device-app.component.html'
})
export class DeviceAppComponent implements OnInit
{
    devices: ChildProducts[] = [];
    recommendedDevices: ChildProducts[];
    lineSummaryList: LineSummaryList[];
    availableCategories: string[];
    selCategory: string;
    searchText: string;

    selectedMTN: string;

    pagingStart: number = 1;
    pagingEnd: number = 100;
    msgs: Message[] = [];



    constructor(
        private _route: ActivatedRoute,
        private _router: Router,
        private _deviceService: DeviceAppService,
        private _cacheService: StateCacheService,
        private _interactionService: InteractionService,
        private _errorHandlingService: ErrorHandlingService
    ){
        this.selectedMTN = _route.snapshot.params['mtn'];
    }


    ngOnInit(): void
    {
        // Lines.
        this.lineSummaryList = this._cacheService.lineSummaryList;
        if(this.lineSummaryList)
        {
            this.lineSummaryList.map(line => line.checked = false);
            let lineToBeSelected = this.lineSummaryList.find(line => line.mtn === this.selectedMTN);
            if (lineToBeSelected) {
                lineToBeSelected.checked = true;
            }
        }


        // Categories.
        this.availableCategories = this._cacheService.availableCategories;
        if(!this.availableCategories)
        {
            this.fetchAvailableCategories();
        }
        this.selCategory = 'Smartphones';



        // Devices.
        this.devices = this._cacheService.devices;
        if(!this.devices)
        {
            this.fetchDevices();
        }


        // Recommended Devices.
        if(this.selectedMTN)
        {
            //this.fetchRecommendedDevices(this.selectedMTN);
        }
    }

    pagedDevices()
    {
        if(this.pagingEnd < 100)
        {
            this.pagingStart = this.pagingEnd + 1;
            this.pagingEnd += 10;

            this.fetchDevices();
        }
    }

    private fetchDevices()
    {
        this.showSpinner(true);

        let devicesReq: DeviceSearch = {
            categoryName: this.selCategory? this.selCategory: '',
            searchText: this.searchText? this.searchText: '',
            start: this.pagingStart,
            end: this.pagingEnd,
        };
        this._deviceService.fetchDevices(devicesReq).
            subscribe((response) => {
                let devicesJSON: DeviceRootObject = response;

                if(!devicesJSON
                    || !devicesJSON.response
                    || !devicesJSON.response.categoryVOList)
                {
                    console.log('No Devices response!');
                    return;
                }

                let freshDevices: ChildProducts[] = devicesJSON.response.categoryVOList[0].childProducts;
                //this.devices.push(freshDevices);

                this.devices = freshDevices;

                this._cacheService.devices = this.devices;
                this.showSpinner(false);
            },
            error => {
                console.log(error);
                this.showSpinner(false);
            });
    }

    private fetchRecommendedDevices(selectedMTN: string)
    {
        let request = {
            mtn: selectedMTN,
        };
        this._deviceService.fetchRecommendDevices(request).
            subscribe((response) => {
                //let recommendedDevicesJSON: DeviceRootObject = response;
                //this.recommendedDevices = recommendedDevicesJSON.response.categoryVOList[0].childProducts;
            },
            error => {
                console.log(error);
            });
    }

    linesSelectionChange(selectedLine: LineSummaryList)
    {
        /*this.lineSummaryList.map(line => {
            if(line.mtn !== selectedLine.mtn)
            {
                line.checked = false;
            }
            return line;
        });*/

        if(!selectedLine.isNewLine)
        {
            //this.fetchRecommendedDevices(selectedLine.mtn);
        }
    }


    resetDevice(selectedLine: LineSummaryList)
    {
        let lineDevice: LineDevice = {
            mld: selectedLine.mldSequence,
            resetAction: 'DEVICE',
        };
        let req: DeviceRequest = {
            action: 'reset',
            inputData: [lineDevice],
        };
        this.showSpinner(true);
        this._deviceService.deviceChange(req).
            subscribe((json) =>
            {
                try
                {
                    let linesMetadata = this.lineSummaryList.map(line => {
                        return {
                            mtn: line.mtn,
                            checked: line.checked,
                            isModified: line.checked,
                            isResetEnabled: (selectedLine.mtn === line.mtn)? false : line.isResetEnabled,
                            isNewLine: line.isNewLine,
                            selectedUpgradeOption: line.selectedUpgradeOption,
                        };
                    });

                    let lineSummaryList = json.response.lineContainer.lineSummaryList;
                    lineSummaryList = lineSummaryList.map(line => {
                        let currentLineMetadata = linesMetadata.find(lineMetadata => {
                            if(lineMetadata.mtn === line.mtn) return true;
                        });
                        if(currentLineMetadata)
                        {
                            line.checked = currentLineMetadata.checked;
                            line.isModified = currentLineMetadata.isModified;
                            line.isResetEnabled = currentLineMetadata.isResetEnabled;
                            line.isNewLine = currentLineMetadata.isNewLine;
                            line.selectedUpgradeOption = currentLineMetadata.selectedUpgradeOption;
                        }
                        return line;
                    });


                    this.lineSummaryList = lineSummaryList;
                    this._cacheService.lineSummaryList = lineSummaryList;

                    let comparisonContainer = json.response.comparisonContainer;
                    this._cacheService.comparisonContainer = comparisonContainer;
                    this._interactionService.publishComparisonContainer(comparisonContainer);

                    this.showSpinner(false);
                }
                catch (error)
                {
                    console.log(error);

                    this.msgs = [];
                    this.msgs.push({
                        severity: 'error',
                        summary: 'Service is unavailable, please try again.',
                        detail: error
                    });
                    this.showSpinner(false);
                }
            },
            error => {
                console.log(error);
                this.showSpinner(false);
            });
    }

    handleSelect(selDevice: ChildProducts)
    {
        this.devices.map(device => {
            if (device.productId === selDevice.productId)
            {
                device.selected = true;
            }
            else {
                device.selected = false;
            }
            return device;
        });
    }

    upgradeDevice()
    {
        let selLines: LineSummaryList[] = this.getSelectedLines();
        if(!selLines || selLines.length <= 0)
        {
            this._errorHandlingService.setErrorMessage('Please select one or more lines.');
            return;
        }

        let selectedDevice:ChildProducts = this.getSelectedDevice();
        if(!selectedDevice)
        {
            this._errorHandlingService.setErrorMessage('Please select a device.');
            return;
        }

        if(selectedDevice && selLines && selLines.length > 0)
        {
            let lineDeviceArr = selLines.map((line: LineSummaryList) =>
            {
                let upgradeOption = (line.selectedUpgradeOption)? line.selectedUpgradeOption:
                                        ((line.upgradeOptions && line.upgradeOptions.length > 0)?
                                            line.upgradeOptions[0]: '');

                let selectedSKU = selectedDevice.childSkus.find(sku => sku.selected);
                let sorId = selectedSKU? selectedSKU.sorId: '';

                let isCPE = false;
                if(!line.isNewLine)
                {
                    isCPE = false;
                }
                else {
                    isCPE = true; // For new line check if customer prefers his own device or not.
                }

                let lineDevice: LineDevice = {
                    mld: line.mldSequence,
                    itemCode: sorId,
                    isNewLine: (line.isNewLine)? line.isNewLine: false,
                    isCPE: isCPE,
                    selTransType: upgradeOption,
                };
                return lineDevice;
            });
            let req: DeviceRequest = {
                action: 'addDevice',
                inputData: lineDeviceArr,
            };
            this.showSpinner(true);
            this._deviceService.deviceChange(req).
                subscribe((json) => {
                    if(!json.success)                    
                    {
                        // Publish error message.

                        this.msgs = [];
                        this.msgs.push({
                            severity: 'error',
                            summary: 'Service is unavailable, please try again.',
                            detail: json.error.errorMessage,
                        });
                        this.showSpinner(false);

                        return;
                    }
                    
                    
                    try {
                        let linesMetadata = this.lineSummaryList.map(line => {
                            return {
                                mtn: line.mtn,
                                checked: line.checked,
                                isModified: line.checked,
                                isResetEnabled: line.checked,
                                isNewLine: line.isNewLine,
                                selectedUpgradeOption: line.selectedUpgradeOption,
                            };
                        });


                        let lineSummaryList = json.response.lineContainer.lineSummaryList;
                        lineSummaryList = lineSummaryList.map(line => {
                            let currentLineMetadata = linesMetadata.find(lineMetadata => {
                                if(lineMetadata.mtn === line.mtn) return true;
                            });
                            if(currentLineMetadata)
                            {
                                line.checked = currentLineMetadata.checked;
                                line.isModified = currentLineMetadata.isModified;
                                line.isResetEnabled = currentLineMetadata.isResetEnabled;
                                line.isNewLine = currentLineMetadata.isNewLine;
                                line.selectedUpgradeOption = currentLineMetadata.selectedUpgradeOption;
                            }
                            return line;
                        });


                        this.lineSummaryList = lineSummaryList;
                        this._cacheService.lineSummaryList = lineSummaryList;

                        let comparisonContainer = json.response.comparisonContainer;
                        this._cacheService.comparisonContainer = comparisonContainer;
                        this._interactionService.publishComparisonContainer(comparisonContainer);

                        this.showSpinner(false);
                    }
                    catch (error)
                    {
                        this.msgs = [];
                        this.msgs.push({
                            severity: 'error',
                            summary: 'Service is unavailable, please try again.',
                            detail: error
                        });
                        this.showSpinner(false);
                    }
                },
                error => {
                    console.log(error);
                    this.showSpinner(false);
                });
        }
    }

    private fetchAvailableCategories()
    {
        this._deviceService.fetchAvailableCategories().subscribe((json) => {
                this.availableCategories = json.response.categoryList;
                this._cacheService.availableCategories = json.response.categoryList;
            },
            error => console.log(error));
    }

    addLine()
    {
        let newLineVO: LineSummaryList = {
            mtn: Math.random().toLocaleString(),
            userName: "New Line1",
            deviceDisplayName: "NPA-XX-XXX",
            isNewLine: true,
            checked: false,
        };

        if(this.lineSummaryList)
        {
            this.lineSummaryList[this.lineSummaryList.length] = newLineVO;
        }
    }

    private getSelectedLines(): LineSummaryList[]
    {
        return this.lineSummaryList.filter(line => {
            if(line.checked) return line;
        });
    }

    private getSelectedDevice(): ChildProducts
    {
        return this.devices.find(device => {
            if(device.selected) return true;
        });
    }

    onCategoryChange()
    {
        this.fetchDevices();
    }

    private showSpinner(show: boolean)
    {
        SpinnerUtils.showSpinner('#main-menu-tabs', show);
    }

    routeToOverview()
    {
        this._router.navigate(['/overview-app']);
        let menuItem: MenuItem = {
            routerLink: '/overview-app'
        };
        this._interactionService.publishMenuItem(menuItem);
    }

}
