import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPassBook } from 'app/shared/model/pass-book.model';
import { PassBookService } from './pass-book.service';

@Component({
  templateUrl: './pass-book-delete-dialog.component.html',
})
export class PassBookDeleteDialogComponent {
  passBook?: IPassBook;

  constructor(protected passBookService: PassBookService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.passBookService.delete(id).subscribe(() => {
      this.eventManager.broadcast('passBookListModification');
      this.activeModal.close();
    });
  }
}
