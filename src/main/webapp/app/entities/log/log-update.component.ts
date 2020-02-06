import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILog } from 'app/shared/model/log.model';
import { LogService } from './log.service';
import { IDipendente } from 'app/shared/model/dipendente.model';
import { DipendenteService } from 'app/entities/dipendente';

@Component({
    selector: 'jhi-log-update',
    templateUrl: './log-update.component.html'
})
export class LogUpdateComponent implements OnInit {
    private _log: ILog;
    isSaving: boolean;

    dipendentes: IDipendente[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private logService: LogService,
        private dipendenteService: DipendenteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ log }) => {
            this.log = log;
        });
        this.dipendenteService.query().subscribe(
            (res: HttpResponse<IDipendente[]>) => {
                this.dipendentes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.log.id !== undefined) {
            this.subscribeToSaveResponse(this.logService.update(this.log));
        } else {
            this.subscribeToSaveResponse(this.logService.create(this.log));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILog>>) {
        result.subscribe((res: HttpResponse<ILog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDipendenteById(index: number, item: IDipendente) {
        return item.id;
    }
    get log() {
        return this._log;
    }

    set log(log: ILog) {
        this._log = log;
    }
}
