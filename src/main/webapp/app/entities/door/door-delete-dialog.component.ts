import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoor } from 'app/shared/model/door.model';
import { DoorService } from './door.service';

@Component({
    selector: 'jhi-door-delete-dialog',
    templateUrl: './door-delete-dialog.component.html'
})
export class DoorDeleteDialogComponent {
    door: IDoor;

    constructor(private doorService: DoorService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.doorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'doorListModification',
                content: 'Deleted an door'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-door-delete-popup',
    template: ''
})
export class DoorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ door }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DoorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.door = door;
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
