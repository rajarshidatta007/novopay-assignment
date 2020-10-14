import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWallet, Wallet } from 'app/shared/model/wallet.model';
import { WalletService } from './wallet.service';
import { WalletComponent } from './wallet.component';
import { WalletDetailComponent } from './wallet-detail.component';
import { WalletUpdateComponent } from './wallet-update.component';

@Injectable({ providedIn: 'root' })
export class WalletResolve implements Resolve<IWallet> {
  constructor(private service: WalletService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWallet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((wallet: HttpResponse<Wallet>) => {
          if (wallet.body) {
            return of(wallet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Wallet());
  }
}

export const walletRoute: Routes = [
  {
    path: '',
    component: WalletComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Wallets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WalletDetailComponent,
    resolve: {
      wallet: WalletResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Wallets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WalletUpdateComponent,
    resolve: {
      wallet: WalletResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Wallets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WalletUpdateComponent,
    resolve: {
      wallet: WalletResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Wallets',
    },
    canActivate: [UserRouteAccessService],
  },
];
