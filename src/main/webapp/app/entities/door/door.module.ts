import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GexinSharedModule } from 'app/shared';
import {
    DoorComponent,
    DoorDetailComponent,
    DoorUpdateComponent,
    DoorDeletePopupComponent,
    DoorDeleteDialogComponent,
    doorRoute,
    doorPopupRoute
} from './';

const ENTITY_STATES = [...doorRoute, ...doorPopupRoute];

@NgModule({
    imports: [GexinSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DoorComponent, DoorDetailComponent, DoorUpdateComponent, DoorDeleteDialogComponent, DoorDeletePopupComponent],
    entryComponents: [DoorComponent, DoorUpdateComponent, DoorDeleteDialogComponent, DoorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GexinDoorModule {}
