import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPassBook, PassBook } from 'app/shared/model/pass-book.model';
import { PassBookService } from './pass-book.service';
import { PassBookComponent } from './pass-book.component';
import { PassBookDetailComponent } from './pass-book-detail.component';
import { PassBookUpdateComponent } from './pass-book-update.component';

@Injectable({ providedIn: 'root' })
export class PassBookResolve implements Resolve<IPassBook> {
  constructor(private service: PassBookService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPassBook> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((passBook: HttpResponse<PassBook>) => {
          if (passBook.body) {
            return of(passBook.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PassBook());
  }
}

export const passBookRoute: Routes = [
  {
    path: '',
    component: PassBookComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PassBooks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PassBookDetailComponent,
    resolve: {
      passBook: PassBookResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PassBooks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PassBookUpdateComponent,
    resolve: {
      passBook: PassBookResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PassBooks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PassBookUpdateComponent,
    resolve: {
      passBook: PassBookResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PassBooks',
    },
    canActivate: [UserRouteAccessService],
  },
];
