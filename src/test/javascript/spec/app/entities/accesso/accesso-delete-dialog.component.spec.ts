/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GexinTestModule } from '../../../test.module';
import { AccessoDeleteDialogComponent } from 'app/entities/accesso/accesso-delete-dialog.component';
import { AccessoService } from 'app/entities/accesso/accesso.service';

describe('Component Tests', () => {
    describe('Accesso Management Delete Component', () => {
        let comp: AccessoDeleteDialogComponent;
        let fixture: ComponentFixture<AccessoDeleteDialogComponent>;
        let service: AccessoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [AccessoDeleteDialogComponent]
            })
                .overrideTemplate(AccessoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccessoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccessoService);
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
