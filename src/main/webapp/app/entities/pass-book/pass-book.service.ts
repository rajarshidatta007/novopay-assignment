import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPassBook } from 'app/shared/model/pass-book.model';

type EntityResponseType = HttpResponse<IPassBook>;
type EntityArrayResponseType = HttpResponse<IPassBook[]>;

@Injectable({ providedIn: 'root' })
export class PassBookService {
  public resourceUrl = SERVER_API_URL + 'api/pass-books';

  constructor(protected http: HttpClient) {}

  create(passBook: IPassBook): Observable<EntityResponseType> {
    return this.http.post<IPassBook>(this.resourceUrl, passBook, { observe: 'response' });
  }

  update(passBook: IPassBook): Observable<EntityResponseType> {
    return this.http.put<IPassBook>(this.resourceUrl, passBook, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPassBook>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPassBook[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
