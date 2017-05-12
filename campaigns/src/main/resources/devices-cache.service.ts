import {Injectable} from "@angular/core";
import {ChildProducts} from "../shared/models/device-app-model";


@Injectable()
export class DevicesCacheService
{
    devices: ChildProducts[];
    recommendedDevices: ChildProducts[];

    availableCategories: string[];
}

