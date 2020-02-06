import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccesso } from 'app/shared/model/accesso.model';

type EntityResponseType = HttpResponse<IAccesso>;
type EntityArrayResponseType = HttpResponse<IAccesso[]>;

@Injectable({ providedIn: 'root' })
export class AccessoService {
    private resourceUrl = SERVER_API_URL + 'api/accessos';

    constructor(private http: HttpClient) {}

    create(accesso: IAccesso): Observable<EntityResponseType> {
        return this.http.post<IAccesso>(this.resourceUrl, accesso, { observe: 'response' });
    }

    update(accesso: IAccesso): Observable<EntityResponseType> {
        return this.http.put<IAccesso>(this.resourceUrl, accesso, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAccesso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAccesso[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
