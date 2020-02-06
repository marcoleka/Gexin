import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAccesso } from 'app/shared/model/accesso.model';
import { AccessoService } from './accesso.service';
import { IGruppo } from 'app/shared/model/gruppo.model';
import { GruppoService } from 'app/entities/gruppo';
import { IDipendente } from 'app/shared/model/dipendente.model';
import { DipendenteService } from 'app/entities/dipendente';

@Component({
    selector: 'jhi-accesso-update',
    templateUrl: './accesso-update.component.html'
})
export class AccessoUpdateComponent implements OnInit {
    private _accesso: IAccesso;
    isSaving: boolean;

    gruppos: IGruppo[];

    dipendentes: IDipendente[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private accessoService: AccessoService,
        private gruppoService: GruppoService,
        private dipendenteService: DipendenteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accesso }) => {
            this.accesso = accesso;
        });
        this.gruppoService.query({ filter: 'accesso-is-null' }).subscribe(
            (res: HttpResponse<IGruppo[]>) => {
                if (!this.accesso.gruppoId) {
                    this.gruppos = res.body;
                } else {
                    this.gruppoService.find(this.accesso.gruppoId).subscribe(
                        (subRes: HttpResponse<IGruppo>) => {
                            this.gruppos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.accesso.id !== undefined) {
            this.subscribeToSaveResponse(this.accessoService.update(this.accesso));
        } else {
            this.subscribeToSaveResponse(this.accessoService.create(this.accesso));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAccesso>>) {
        result.subscribe((res: HttpResponse<IAccesso>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGruppoById(index: number, item: IGruppo) {
        return item.id;
    }

    trackDipendenteById(index: number, item: IDipendente) {
        return item.id;
    }
    get accesso() {
        return this._accesso;
    }

    set accesso(accesso: IAccesso) {
        this._accesso = accesso;
    }
}
