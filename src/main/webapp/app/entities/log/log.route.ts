import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Log } from 'app/shared/model/log.model';
import { LogService } from './log.service';
import { LogComponent } from './log.component';
import { LogDetailComponent } from './log-detail.component';
import { LogUpdateComponent } from './log-update.component';
import { LogDeletePopupComponent } from './log-delete-dialog.component';
import { ILog } from 'app/shared/model/log.model';

@Injectable({ providedIn: 'root' })
export class LogResolve implements Resolve<ILog> {
    constructor(private service: LogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((log: HttpResponse<Log>) => log.body));
        }
        return of(new Log());
    }
}

export const logRoute: Routes = [
    {
        path: 'log',
        component: LogComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Logs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'log/:id/view',
        component: LogDetailComponent,
        resolve: {
            log: LogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'log/new',
        component: LogUpdateComponent,
        resolve: {
            log: LogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'log/:id/edit',
        component: LogUpdateComponent,
        resolve: {
            log: LogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logPopupRoute: Routes = [
    {
        path: 'log/:id/delete',
        component: LogDeletePopupComponent,
        resolve: {
            log: LogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
