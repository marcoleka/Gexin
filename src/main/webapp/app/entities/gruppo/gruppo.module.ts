import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GexinSharedModule } from 'app/shared';
import {
    GruppoComponent,
    GruppoDetailComponent,
    GruppoUpdateComponent,
    GruppoDeletePopupComponent,
    GruppoDeleteDialogComponent,
    gruppoRoute,
    gruppoPopupRoute
} from './';

const ENTITY_STATES = [...gruppoRoute, ...gruppoPopupRoute];

@NgModule({
    imports: [GexinSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GruppoComponent, GruppoDetailComponent, GruppoUpdateComponent, GruppoDeleteDialogComponent, GruppoDeletePopupComponent],
    entryComponents: [GruppoComponent, GruppoUpdateComponent, GruppoDeleteDialogComponent, GruppoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GexinGruppoModule {}
