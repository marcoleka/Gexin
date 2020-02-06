/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GexinTestModule } from '../../../test.module';
import { TimeZoneDeleteDialogComponent } from 'app/entities/time-zone/time-zone-delete-dialog.component';
import { TimeZoneService } from 'app/entities/time-zone/time-zone.service';

describe('Component Tests', () => {
    describe('TimeZone Management Delete Component', () => {
        let comp: TimeZoneDeleteDialogComponent;
        let fixture: ComponentFixture<TimeZoneDeleteDialogComponent>;
        let service: TimeZoneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [TimeZoneDeleteDialogComponent]
            })
                .overrideTemplate(TimeZoneDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimeZoneDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeZoneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
