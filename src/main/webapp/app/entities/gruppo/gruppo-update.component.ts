import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGruppo } from 'app/shared/model/gruppo.model';
import { GruppoService } from './gruppo.service';
import { IDoor } from 'app/shared/model/door.model';
import { DoorService } from 'app/entities/door';
import { IAccesso } from 'app/shared/model/accesso.model';
import { AccessoService } from 'app/entities/accesso';

@Component({
    selector: 'jhi-gruppo-update',
    templateUrl: './gruppo-update.component.html'
})
export class GruppoUpdateComponent implements OnInit {
    private _gruppo: IGruppo;
    isSaving: boolean;

    doors: IDoor[];

    accessos: IAccesso[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private gruppoService: GruppoService,
        private doorService: DoorService,
        private accessoService: AccessoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gruppo }) => {
            this.gruppo = gruppo;
        });
        this.doorService.query().subscribe(
            (res: HttpResponse<IDoor[]>) => {
                this.doors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.gruppo.id !== undefined) {
            this.subscribeToSaveResponse(this.gruppoService.update(this.gruppo));
        } else {
            this.subscribeToSaveResponse(this.gruppoService.create(this.gruppo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGruppo>>) {
        result.subscribe((res: HttpResponse<IGruppo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDoorById(index: number, item: IDoor) {
        return item.id;
    }

    trackAccessoById(index: number, item: IAccesso) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get gruppo() {
        return this._gruppo;
    }

    set gruppo(gruppo: IGruppo) {
        this._gruppo = gruppo;
    }
}
