import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from 'app/entities/wallet/wallet.service';
import { IPassBook } from 'app/shared/model/pass-book.model';
import { PassBookService } from 'app/entities/pass-book/pass-book.service';

type SelectableEntity = IWallet | IPassBook;

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  receivers: IWallet[] = [];
  senders: IWallet[] = [];
  passbooks: IPassBook[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    charges: [],
    receiverId: [],
    senderId: [],
    passbookId: [],
  });

  constructor(
    protected transactionService: TransactionService,
    protected walletService: WalletService,
    protected passBookService: PassBookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);

      this.walletService
        .query({ filter: 'transaction-is-null' })
        .pipe(
          map((res: HttpResponse<IWallet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IWallet[]) => {
          if (!transaction.receiverId) {
            this.receivers = resBody;
          } else {
            this.walletService
              .find(transaction.receiverId)
              .pipe(
                map((subRes: HttpResponse<IWallet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IWallet[]) => (this.receivers = concatRes));
          }
        });

      this.walletService
        .query({ filter: 'transaction-is-null' })
        .pipe(
          map((res: HttpResponse<IWallet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IWallet[]) => {
          if (!transaction.senderId) {
            this.senders = resBody;
          } else {
            this.walletService
              .find(transaction.senderId)
              .pipe(
                map((subRes: HttpResponse<IWallet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IWallet[]) => (this.senders = concatRes));
          }
        });

      this.passBookService.query().subscribe((res: HttpResponse<IPassBook[]>) => (this.passbooks = res.body || []));
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      amount: transaction.amount,
      charges: transaction.charges,
      receiverId: transaction.receiverId,
      senderId: transaction.senderId,
      passbookId: transaction.passbookId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      charges: this.editForm.get(['charges'])!.value,
      receiverId: this.editForm.get(['receiverId'])!.value,
      senderId: this.editForm.get(['senderId'])!.value,
      passbookId: this.editForm.get(['passbookId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
