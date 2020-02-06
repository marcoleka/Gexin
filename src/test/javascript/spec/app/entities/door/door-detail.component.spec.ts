/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GexinTestModule } from '../../../test.module';
import { DoorDetailComponent } from 'app/entities/door/door-detail.component';
import { Door } from 'app/shared/model/door.model';

describe('Component Tests', () => {
    describe('Door Management Detail Component', () => {
        let comp: DoorDetailComponent;
        let fixture: ComponentFixture<DoorDetailComponent>;
        const route = ({ data: of({ door: new Door(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [DoorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DoorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DoorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.door).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
