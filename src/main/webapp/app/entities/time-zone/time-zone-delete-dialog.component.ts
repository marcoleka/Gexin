import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimeZone } from 'app/shared/model/time-zone.model';
import { TimeZoneService } from './time-zone.service';

@Component({
    selector: 'jhi-time-zone-delete-dialog',
    templateUrl: './time-zone-delete-dialog.component.html'
})
export class TimeZoneDeleteDialogComponent {
    timeZone: ITimeZone;

    constructor(private timeZoneService: TimeZoneService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timeZoneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timeZoneListModification',
                content: 'Deleted an timeZone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-time-zone-delete-popup',
    template: ''
})
export class TimeZoneDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timeZone }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TimeZoneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.timeZone = timeZone;
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
