import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'wallet',
        loadChildren: () => import('./wallet/wallet.module').then(m => m.NovopayassignmentWalletModule),
      },
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.NovopayassignmentTransactionModule),
      },
      {
        path: 'pass-book',
        loadChildren: () => import('./pass-book/pass-book.module').then(m => m.NovopayassignmentPassBookModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class NovopayassignmentEntityModule {}
