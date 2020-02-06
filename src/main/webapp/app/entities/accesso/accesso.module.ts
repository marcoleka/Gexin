import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GexinSharedModule } from 'app/shared';
import {
    AccessoComponent,
    AccessoDetailComponent,
    AccessoUpdateComponent,
    AccessoDeletePopupComponent,
    AccessoDeleteDialogComponent,
    accessoRoute,
    accessoPopupRoute
} from './';

const ENTITY_STATES = [...accessoRoute, ...accessoPopupRoute];

@NgModule({
    imports: [GexinSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccessoComponent,
        AccessoDetailComponent,
        AccessoUpdateComponent,
        AccessoDeleteDialogComponent,
        AccessoDeletePopupComponent
    ],
    entryComponents: [AccessoComponent, AccessoUpdateComponent, AccessoDeleteDialogComponent, AccessoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GexinAccessoModule {}
