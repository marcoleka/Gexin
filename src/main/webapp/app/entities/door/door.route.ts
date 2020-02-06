import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Door } from 'app/shared/model/door.model';
import { DoorService } from './door.service';
import { DoorComponent } from './door.component';
import { DoorDetailComponent } from './door-detail.component';
import { DoorUpdateComponent } from './door-update.component';
import { DoorDeletePopupComponent } from './door-delete-dialog.component';
import { IDoor } from 'app/shared/model/door.model';

@Injectable({ providedIn: 'root' })
export class DoorResolve implements Resolve<IDoor> {
    constructor(private service: DoorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((door: HttpResponse<Door>) => door.body));
        }
        return of(new Door());
    }
}

export const doorRoute: Routes = [
    {
        path: 'door',
        component: DoorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Doors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'door/:id/view',
        component: DoorDetailComponent,
        resolve: {
            door: DoorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'door/new',
        component: DoorUpdateComponent,
        resolve: {
            door: DoorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'door/:id/edit',
        component: DoorUpdateComponent,
        resolve: {
            door: DoorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const doorPopupRoute: Routes = [
    {
        path: 'door/:id/delete',
        component: DoorDeletePopupComponent,
        resolve: {
            door: DoorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
