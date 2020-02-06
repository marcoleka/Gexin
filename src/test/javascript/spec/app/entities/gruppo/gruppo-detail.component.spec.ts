/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GexinTestModule } from '../../../test.module';
import { GruppoDetailComponent } from 'app/entities/gruppo/gruppo-detail.component';
import { Gruppo } from 'app/shared/model/gruppo.model';

describe('Component Tests', () => {
    describe('Gruppo Management Detail Component', () => {
        let comp: GruppoDetailComponent;
        let fixture: ComponentFixture<GruppoDetailComponent>;
        const route = ({ data: of({ gruppo: new Gruppo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [GruppoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GruppoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GruppoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.gruppo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
