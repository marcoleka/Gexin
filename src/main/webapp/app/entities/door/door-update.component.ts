import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDoor } from 'app/shared/model/door.model';
import { DoorService } from './door.service';
import { IGruppo } from 'app/shared/model/gruppo.model';
import { GruppoService } from 'app/entities/gruppo';

@Component({
    selector: 'jhi-door-update',
    templateUrl: './door-update.component.html'
})
export class DoorUpdateComponent implements OnInit {
    private _door: IDoor;
    isSaving: boolean;

    gruppos: IGruppo[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private doorService: DoorService,
        private gruppoService: GruppoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ door }) => {
            this.door = door;
        });
        this.gruppoService.query().subscribe(
            (res: HttpResponse<IGruppo[]>) => {
                this.gruppos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.door.id !== undefined) {
            this.subscribeToSaveResponse(this.doorService.update(this.door));
        } else {
            this.subscribeToSaveResponse(this.doorService.create(this.door));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDoor>>) {
        result.subscribe((res: HttpResponse<IDoor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get door() {
        return this._door;
    }

    set door(door: IDoor) {
        this._door = door;
    }
}
