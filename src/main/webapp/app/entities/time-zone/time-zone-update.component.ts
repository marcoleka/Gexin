import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITimeZone } from 'app/shared/model/time-zone.model';
import { TimeZoneService } from './time-zone.service';
import { IAccesso } from 'app/shared/model/accesso.model';
import { AccessoService } from 'app/entities/accesso';

@Component({
    selector: 'jhi-time-zone-update',
    templateUrl: './time-zone-update.component.html'
})
export class TimeZoneUpdateComponent implements OnInit {
    private _timeZone: ITimeZone;
    isSaving: boolean;

    accessos: IAccesso[];
    da1: string;
    da2: string;
    da3: string;
    da4: string;
    a1: string;
    a2: string;
    a3: string;
    a4: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private timeZoneService: TimeZoneService,
        private accessoService: AccessoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timeZone }) => {
            this.timeZone = timeZone;
        });
        this.accessoService.query().subscribe(
            (res: HttpResponse<IAccesso[]>) => {
                this.accessos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.timeZone.da1 = moment(this.da1, DATE_TIME_FORMAT);
        this.timeZone.da2 = moment(this.da2, DATE_TIME_FORMAT);
        this.timeZone.da3 = moment(this.da3, DATE_TIME_FORMAT);
        this.timeZone.da4 = moment(this.da4, DATE_TIME_FORMAT);
        this.timeZone.a1 = moment(this.a1, DATE_TIME_FORMAT);
        this.timeZone.a2 = moment(this.a2, DATE_TIME_FORMAT);
        this.timeZone.a3 = moment(this.a3, DATE_TIME_FORMAT);
        this.timeZone.a4 = moment(this.a4, DATE_TIME_FORMAT);
        if (this.timeZone.id !== undefined) {
            this.subscribeToSaveResponse(this.timeZoneService.update(this.timeZone));
        } else {
            this.subscribeToSaveResponse(this.timeZoneService.create(this.timeZone));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITimeZone>>) {
        result.subscribe((res: HttpResponse<ITimeZone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAccessoById(index: number, item: IAccesso) {
        return item.id;
    }
    get timeZone() {
        return this._timeZone;
    }

    set timeZone(timeZone: ITimeZone) {
        this._timeZone = timeZone;
        this.da1 = moment(timeZone.da1).format(DATE_TIME_FORMAT);
        this.da2 = moment(timeZone.da2).format(DATE_TIME_FORMAT);
        this.da3 = moment(timeZone.da3).format(DATE_TIME_FORMAT);
        this.da4 = moment(timeZone.da4).format(DATE_TIME_FORMAT);
        this.a1 = moment(timeZone.a1).format(DATE_TIME_FORMAT);
        this.a2 = moment(timeZone.a2).format(DATE_TIME_FORMAT);
        this.a3 = moment(timeZone.a3).format(DATE_TIME_FORMAT);
        this.a4 = moment(timeZone.a4).format(DATE_TIME_FORMAT);
    }
}
