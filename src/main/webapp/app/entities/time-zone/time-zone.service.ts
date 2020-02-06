import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITimeZone } from 'app/shared/model/time-zone.model';

type EntityResponseType = HttpResponse<ITimeZone>;
type EntityArrayResponseType = HttpResponse<ITimeZone[]>;

@Injectable({ providedIn: 'root' })
export class TimeZoneService {
    private resourceUrl = SERVER_API_URL + 'api/time-zones';

    constructor(private http: HttpClient) {}

    create(timeZone: ITimeZone): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(timeZone);
        return this.http
            .post<ITimeZone>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(timeZone: ITimeZone): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(timeZone);
        return this.http
            .put<ITimeZone>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITimeZone>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITimeZone[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(timeZone: ITimeZone): ITimeZone {
        const copy: ITimeZone = Object.assign({}, timeZone, {
            da1: timeZone.da1 != null && timeZone.da1.isValid() ? timeZone.da1.toJSON() : null,
            da2: timeZone.da2 != null && timeZone.da2.isValid() ? timeZone.da2.toJSON() : null,
            da3: timeZone.da3 != null && timeZone.da3.isValid() ? timeZone.da3.toJSON() : null,
            da4: timeZone.da4 != null && timeZone.da4.isValid() ? timeZone.da4.toJSON() : null,
            a1: timeZone.a1 != null && timeZone.a1.isValid() ? timeZone.a1.toJSON() : null,
            a2: timeZone.a2 != null && timeZone.a2.isValid() ? timeZone.a2.toJSON() : null,
            a3: timeZone.a3 != null && timeZone.a3.isValid() ? timeZone.a3.toJSON() : null,
            a4: timeZone.a4 != null && timeZone.a4.isValid() ? timeZone.a4.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.da1 = res.body.da1 != null ? moment(res.body.da1) : null;
        res.body.da2 = res.body.da2 != null ? moment(res.body.da2) : null;
        res.body.da3 = res.body.da3 != null ? moment(res.body.da3) : null;
        res.body.da4 = res.body.da4 != null ? moment(res.body.da4) : null;
        res.body.a1 = res.body.a1 != null ? moment(res.body.a1) : null;
        res.body.a2 = res.body.a2 != null ? moment(res.body.a2) : null;
        res.body.a3 = res.body.a3 != null ? moment(res.body.a3) : null;
        res.body.a4 = res.body.a4 != null ? moment(res.body.a4) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((timeZone: ITimeZone) => {
            timeZone.da1 = timeZone.da1 != null ? moment(timeZone.da1) : null;
            timeZone.da2 = timeZone.da2 != null ? moment(timeZone.da2) : null;
            timeZone.da3 = timeZone.da3 != null ? moment(timeZone.da3) : null;
            timeZone.da4 = timeZone.da4 != null ? moment(timeZone.da4) : null;
            timeZone.a1 = timeZone.a1 != null ? moment(timeZone.a1) : null;
            timeZone.a2 = timeZone.a2 != null ? moment(timeZone.a2) : null;
            timeZone.a3 = timeZone.a3 != null ? moment(timeZone.a3) : null;
            timeZone.a4 = timeZone.a4 != null ? moment(timeZone.a4) : null;
        });
        return res;
    }
}
