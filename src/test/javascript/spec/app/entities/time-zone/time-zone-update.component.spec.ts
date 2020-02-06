/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GexinTestModule } from '../../../test.module';
import { TimeZoneUpdateComponent } from 'app/entities/time-zone/time-zone-update.component';
import { TimeZoneService } from 'app/entities/time-zone/time-zone.service';
import { TimeZone } from 'app/shared/model/time-zone.model';

describe('Component Tests', () => {
    describe('TimeZone Management Update Component', () => {
        let comp: TimeZoneUpdateComponent;
        let fixture: ComponentFixture<TimeZoneUpdateComponent>;
        let service: TimeZoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [TimeZoneUpdateComponent]
            })
                .overrideTemplate(TimeZoneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimeZoneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeZoneService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TimeZone(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timeZone = entity;
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
                    const entity = new TimeZone();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timeZone = entity;
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
