/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GexinTestModule } from '../../../test.module';
import { GruppoDeleteDialogComponent } from 'app/entities/gruppo/gruppo-delete-dialog.component';
import { GruppoService } from 'app/entities/gruppo/gruppo.service';

describe('Component Tests', () => {
    describe('Gruppo Management Delete Component', () => {
        let comp: GruppoDeleteDialogComponent;
        let fixture: ComponentFixture<GruppoDeleteDialogComponent>;
        let service: GruppoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [GruppoDeleteDialogComponent]
            })
                .overrideTemplate(GruppoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GruppoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GruppoService);
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
