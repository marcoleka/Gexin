import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GexinSharedModule } from 'app/shared';
import {
    TimeZoneComponent,
    TimeZoneDetailComponent,
    TimeZoneUpdateComponent,
    TimeZoneDeletePopupComponent,
    TimeZoneDeleteDialogComponent,
    timeZoneRoute,
    timeZonePopupRoute
} from './';

const ENTITY_STATES = [...timeZoneRoute, ...timeZonePopupRoute];

@NgModule({
    imports: [GexinSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimeZoneComponent,
        TimeZoneDetailComponent,
        TimeZoneUpdateComponent,
        TimeZoneDeleteDialogComponent,
        TimeZoneDeletePopupComponent
    ],
    entryComponents: [TimeZoneComponent, TimeZoneUpdateComponent, TimeZoneDeleteDialogComponent, TimeZoneDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GexinTimeZoneModule {}
