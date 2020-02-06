import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GexinAccessoModule } from './accesso/accesso.module';
import { GexinTimeZoneModule } from './time-zone/time-zone.module';
import { GexinDoorModule } from './door/door.module';
import { GexinGruppoModule } from './gruppo/gruppo.module';
import { GexinLogModule } from './log/log.module';
import { GexinDipendenteModule } from './dipendente/dipendente.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GexinAccessoModule,
        GexinTimeZoneModule,
        GexinDoorModule,
        GexinGruppoModule,
        GexinLogModule,
        GexinDipendenteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GexinEntityModule {}
