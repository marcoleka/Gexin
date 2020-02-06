import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDipendente } from 'app/shared/model/dipendente.model';
import { DipendenteService } from './dipendente.service';

@Component({
    selector: 'jhi-dipendente-delete-dialog',
    templateUrl: './dipendente-delete-dialog.component.html'
})
export class DipendenteDeleteDialogComponent {
    dipendente: IDipendente;

    constructor(private dipendenteService: DipendenteService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dipendenteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dipendenteListModification',
                content: 'Deleted an dipendente'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dipendente-delete-popup',
    template: ''
})
export class DipendenteDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dipendente }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DipendenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dipendente = dipendente;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
