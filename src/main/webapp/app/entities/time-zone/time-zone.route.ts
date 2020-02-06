import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TimeZone } from 'app/shared/model/time-zone.model';
import { TimeZoneService } from './time-zone.service';
import { TimeZoneComponent } from './time-zone.component';
import { TimeZoneDetailComponent } from './time-zone-detail.component';
import { TimeZoneUpdateComponent } from './time-zone-update.component';
import { TimeZoneDeletePopupComponent } from './time-zone-delete-dialog.component';
import { ITimeZone } from 'app/shared/model/time-zone.model';

@Injectable({ providedIn: 'root' })
export class TimeZoneResolve implements Resolve<ITimeZone> {
    constructor(private service: TimeZoneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((timeZone: HttpResponse<TimeZone>) => timeZone.body));
        }
        return of(new TimeZone());
    }
}

export const timeZoneRoute: Routes = [
    {
        path: 'time-zone',
        component: TimeZoneComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'TimeZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-zone/:id/view',
        component: TimeZoneDetailComponent,
        resolve: {
            timeZone: TimeZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-zone/new',
        component: TimeZoneUpdateComponent,
        resolve: {
            timeZone: TimeZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-zone/:id/edit',
        component: TimeZoneUpdateComponent,
        resolve: {
            timeZone: TimeZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeZones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timeZonePopupRoute: Routes = [
    {
        path: 'time-zone/:id/delete',
        component: TimeZoneDeletePopupComponent,
        resolve: {
            timeZone: TimeZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeZones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
