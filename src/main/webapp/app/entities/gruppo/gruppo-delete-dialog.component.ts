import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGruppo } from 'app/shared/model/gruppo.model';
import { GruppoService } from './gruppo.service';

@Component({
    selector: 'jhi-gruppo-delete-dialog',
    templateUrl: './gruppo-delete-dialog.component.html'
})
export class GruppoDeleteDialogComponent {
    gruppo: IGruppo;

    constructor(private gruppoService: GruppoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gruppoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'gruppoListModification',
                content: 'Deleted an gruppo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gruppo-delete-popup',
    template: ''
})
export class GruppoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gruppo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GruppoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.gruppo = gruppo;
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
