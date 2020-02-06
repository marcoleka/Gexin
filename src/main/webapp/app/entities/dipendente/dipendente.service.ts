import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDipendente } from 'app/shared/model/dipendente.model';

type EntityResponseType = HttpResponse<IDipendente>;
type EntityArrayResponseType = HttpResponse<IDipendente[]>;

@Injectable({ providedIn: 'root' })
export class DipendenteService {
    private resourceUrl = SERVER_API_URL + 'api/dipendentes';

    constructor(private http: HttpClient) {}

    create(dipendente: IDipendente): Observable<EntityResponseType> {
        return this.http.post<IDipendente>(this.resourceUrl, dipendente, { observe: 'response' });
    }

    update(dipendente: IDipendente): Observable<EntityResponseType> {
        return this.http.put<IDipendente>(this.resourceUrl, dipendente, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDipendente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDipendente[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
