import { CustomReuseStrategy } from './../CustomReuseStrategy';
import { NgModule } from '@angular/core';
import { Routes, RouterModule,RouteReuseStrategy } from "@angular/router";
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";

import { DeviceAppComponent } from "./device-app.component";
import { ListDeviceComponent } from './list-device/list-device.component';
import { DeviceDetailsComponent } from './device-details/device-details.component';
import {DevicesCacheService} from "./devices-cache.service";
import { GrowlModule } from 'primeng/primeng';



const moduleRoutes: Routes = [
    {
        path: '',
        component: DeviceAppComponent
    },
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        GrowlModule,
        RouterModule.forChild(moduleRoutes),
    ],
    declarations: [
        DeviceAppComponent,
        ListDeviceComponent,
        DeviceDetailsComponent
    ],
    providers: [
        {provide: RouteReuseStrategy, useClass: CustomReuseStrategy}
    ],
})
export class DeviceAppModule {

}
