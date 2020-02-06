/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GexinTestModule } from '../../../test.module';
import { TimeZoneDetailComponent } from 'app/entities/time-zone/time-zone-detail.component';
import { TimeZone } from 'app/shared/model/time-zone.model';

describe('Component Tests', () => {
    describe('TimeZone Management Detail Component', () => {
        let comp: TimeZoneDetailComponent;
        let fixture: ComponentFixture<TimeZoneDetailComponent>;
        const route = ({ data: of({ timeZone: new TimeZone(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [TimeZoneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TimeZoneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimeZoneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.timeZone).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
