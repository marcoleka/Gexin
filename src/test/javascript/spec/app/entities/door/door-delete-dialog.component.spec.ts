/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GexinTestModule } from '../../../test.module';
import { DoorDeleteDialogComponent } from 'app/entities/door/door-delete-dialog.component';
import { DoorService } from 'app/entities/door/door.service';

describe('Component Tests', () => {
    describe('Door Management Delete Component', () => {
        let comp: DoorDeleteDialogComponent;
        let fixture: ComponentFixture<DoorDeleteDialogComponent>;
        let service: DoorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [DoorDeleteDialogComponent]
            })
                .overrideTemplate(DoorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DoorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DoorService);
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
