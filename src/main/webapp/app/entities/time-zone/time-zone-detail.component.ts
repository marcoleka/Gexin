import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimeZone } from 'app/shared/model/time-zone.model';

@Component({
    selector: 'jhi-time-zone-detail',
    templateUrl: './time-zone-detail.component.html'
})
export class TimeZoneDetailComponent implements OnInit {
    timeZone: ITimeZone;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timeZone }) => {
            this.timeZone = timeZone;
        });
    }

    previousState() {
        window.history.back();
    }
}
