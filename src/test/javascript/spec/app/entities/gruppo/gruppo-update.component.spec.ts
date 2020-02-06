/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GexinTestModule } from '../../../test.module';
import { GruppoUpdateComponent } from 'app/entities/gruppo/gruppo-update.component';
import { GruppoService } from 'app/entities/gruppo/gruppo.service';
import { Gruppo } from 'app/shared/model/gruppo.model';

describe('Component Tests', () => {
    describe('Gruppo Management Update Component', () => {
        let comp: GruppoUpdateComponent;
        let fixture: ComponentFixture<GruppoUpdateComponent>;
        let service: GruppoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [GruppoUpdateComponent]
            })
                .overrideTemplate(GruppoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GruppoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GruppoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Gruppo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gruppo = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Gruppo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gruppo = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
