import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from './wallet.service';

@Component({
  templateUrl: './wallet-delete-dialog.component.html',
})
export class WalletDeleteDialogComponent {
  wallet?: IWallet;

  constructor(protected walletService: WalletService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.walletService.delete(id).subscribe(() => {
      this.eventManager.broadcast('walletListModification');
      this.activeModal.close();
    });
  }
}
