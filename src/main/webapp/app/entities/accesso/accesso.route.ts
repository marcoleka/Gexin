import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Accesso } from 'app/shared/model/accesso.model';
import { AccessoService } from './accesso.service';
import { AccessoComponent } from './accesso.component';
import { AccessoDetailComponent } from './accesso-detail.component';
import { AccessoUpdateComponent } from './accesso-update.component';
import { AccessoDeletePopupComponent } from './accesso-delete-dialog.component';
import { IAccesso } from 'app/shared/model/accesso.model';

@Injectable({ providedIn: 'root' })
export class AccessoResolve implements Resolve<IAccesso> {
    constructor(private service: AccessoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((accesso: HttpResponse<Accesso>) => accesso.body));
        }
        return of(new Accesso());
    }
}

export const accessoRoute: Routes = [
    {
        path: 'accesso',
        component: AccessoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Accessos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accesso/:id/view',
        component: AccessoDetailComponent,
        resolve: {
            accesso: AccessoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Accessos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accesso/new',
        component: AccessoUpdateComponent,
        resolve: {
            accesso: AccessoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Accessos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accesso/:id/edit',
        component: AccessoUpdateComponent,
        resolve: {
            accesso: AccessoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Accessos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accessoPopupRoute: Routes = [
    {
        path: 'accesso/:id/delete',
        component: AccessoDeletePopupComponent,
        resolve: {
            accesso: AccessoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Accessos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
