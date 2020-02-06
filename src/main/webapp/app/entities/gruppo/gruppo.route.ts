import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Gruppo } from 'app/shared/model/gruppo.model';
import { GruppoService } from './gruppo.service';
import { GruppoComponent } from './gruppo.component';
import { GruppoDetailComponent } from './gruppo-detail.component';
import { GruppoUpdateComponent } from './gruppo-update.component';
import { GruppoDeletePopupComponent } from './gruppo-delete-dialog.component';
import { IGruppo } from 'app/shared/model/gruppo.model';

@Injectable({ providedIn: 'root' })
export class GruppoResolve implements Resolve<IGruppo> {
    constructor(private service: GruppoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((gruppo: HttpResponse<Gruppo>) => gruppo.body));
        }
        return of(new Gruppo());
    }
}

export const gruppoRoute: Routes = [
    {
        path: 'gruppo',
        component: GruppoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Gruppos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gruppo/:id/view',
        component: GruppoDetailComponent,
        resolve: {
            gruppo: GruppoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gruppos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gruppo/new',
        component: GruppoUpdateComponent,
        resolve: {
            gruppo: GruppoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gruppos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gruppo/:id/edit',
        component: GruppoUpdateComponent,
        resolve: {
            gruppo: GruppoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gruppos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gruppoPopupRoute: Routes = [
    {
        path: 'gruppo/:id/delete',
        component: GruppoDeletePopupComponent,
        resolve: {
            gruppo: GruppoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gruppos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
