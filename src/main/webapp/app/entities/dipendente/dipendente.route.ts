import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Dipendente } from 'app/shared/model/dipendente.model';
import { DipendenteService } from './dipendente.service';
import { DipendenteComponent } from './dipendente.component';
import { DipendenteDetailComponent } from './dipendente-detail.component';
import { DipendenteUpdateComponent } from './dipendente-update.component';
import { DipendenteDeletePopupComponent } from './dipendente-delete-dialog.component';
import { IDipendente } from 'app/shared/model/dipendente.model';

@Injectable({ providedIn: 'root' })
export class DipendenteResolve implements Resolve<IDipendente> {
    constructor(private service: DipendenteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dipendente: HttpResponse<Dipendente>) => dipendente.body));
        }
        return of(new Dipendente());
    }
}

export const dipendenteRoute: Routes = [
    {
        path: 'dipendente',
        component: DipendenteComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Dipendentes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dipendente/:id/view',
        component: DipendenteDetailComponent,
        resolve: {
            dipendente: DipendenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dipendentes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dipendente/new',
        component: DipendenteUpdateComponent,
        resolve: {
            dipendente: DipendenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dipendentes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dipendente/:id/edit',
        component: DipendenteUpdateComponent,
        resolve: {
            dipendente: DipendenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dipendentes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dipendentePopupRoute: Routes = [
    {
        path: 'dipendente/:id/delete',
        component: DipendenteDeletePopupComponent,
        resolve: {
            dipendente: DipendenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dipendentes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
