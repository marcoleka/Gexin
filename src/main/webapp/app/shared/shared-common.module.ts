import { NgModule } from '@angular/core';

import { GexinSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [GexinSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [GexinSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class GexinSharedCommonModule {}
