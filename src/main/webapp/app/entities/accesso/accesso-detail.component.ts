import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccesso } from 'app/shared/model/accesso.model';

@Component({
    selector: 'jhi-accesso-detail',
    templateUrl: './accesso-detail.component.html'
})
export class AccessoDetailComponent implements OnInit {
    accesso: IAccesso;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accesso }) => {
            this.accesso = accesso;
        });
    }

    previousState() {
        window.history.back();
    }
}
