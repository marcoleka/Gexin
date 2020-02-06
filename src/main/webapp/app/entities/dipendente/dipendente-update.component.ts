import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDipendente } from 'app/shared/model/dipendente.model';
import { DipendenteService } from './dipendente.service';
import { IAccesso } from 'app/shared/model/accesso.model';
import { AccessoService } from 'app/entities/accesso';

@Component({
    selector: 'jhi-dipendente-update',
    templateUrl: './dipendente-update.component.html'
})
export class DipendenteUpdateComponent implements OnInit {
    private _dipendente: IDipendente;
    isSaving: boolean;

    accessos: IAccesso[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private dipendenteService: DipendenteService,
        private accessoService: AccessoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dipendente }) => {
            this.dipendente = dipendente;
        });
        this.accessoService.query({ filter: 'dipendente-is-null' }).subscribe(
            (res: HttpResponse<IAccesso[]>) => {
                if (!this.dipendente.accessoId) {
                    this.accessos = res.body;
                } else {
                    this.accessoService.find(this.dipendente.accessoId).subscribe(
                        (subRes: HttpResponse<IAccesso>) => {
                            this.accessos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dipendente.id !== undefined) {
            this.subscribeToSaveResponse(this.dipendenteService.update(this.dipendente));
        } else {
            this.subscribeToSaveResponse(this.dipendenteService.create(this.dipendente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDipendente>>) {
        result.subscribe((res: HttpResponse<IDipendente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get dipendente() {
        return this._dipendente;
    }

    set dipendente(dipendente: IDipendente) {
        this._dipendente = dipendente;
    }
}
