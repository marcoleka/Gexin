import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGruppo } from 'app/shared/model/gruppo.model';

@Component({
    selector: 'jhi-gruppo-detail',
    templateUrl: './gruppo-detail.component.html'
})
export class GruppoDetailComponent implements OnInit {
    gruppo: IGruppo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gruppo }) => {
            this.gruppo = gruppo;
        });
    }

    previousState() {
        window.history.back();
    }
}
