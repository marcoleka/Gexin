/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GexinTestModule } from '../../../test.module';
import { AccessoDetailComponent } from 'app/entities/accesso/accesso-detail.component';
import { Accesso } from 'app/shared/model/accesso.model';

describe('Component Tests', () => {
    describe('Accesso Management Detail Component', () => {
        let comp: AccessoDetailComponent;
        let fixture: ComponentFixture<AccessoDetailComponent>;
        const route = ({ data: of({ accesso: new Accesso(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GexinTestModule],
                declarations: [AccessoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AccessoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccessoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.accesso).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
