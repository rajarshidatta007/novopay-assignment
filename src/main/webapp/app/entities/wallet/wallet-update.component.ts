import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IWallet, Wallet } from 'app/shared/model/wallet.model';
import { WalletService } from './wallet.service';
import { IPassBook } from 'app/shared/model/pass-book.model';
import { PassBookService } from 'app/entities/pass-book/pass-book.service';

@Component({
  selector: 'jhi-wallet-update',
  templateUrl: './wallet-update.component.html',
})
export class WalletUpdateComponent implements OnInit {
  isSaving = false;
  passbooks: IPassBook[] = [];

  editForm = this.fb.group({
    id: [],
    balance: [],
    owenerId: [],
    passbookId: [],
  });

  constructor(
    protected walletService: WalletService,
    protected passBookService: PassBookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wallet }) => {
      this.updateForm(wallet);

      this.passBookService
        .query({ filter: 'wallet-is-null' })
        .pipe(
          map((res: HttpResponse<IPassBook[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPassBook[]) => {
          if (!wallet.passbookId) {
            this.passbooks = resBody;
          } else {
            this.passBookService
              .find(wallet.passbookId)
              .pipe(
                map((subRes: HttpResponse<IPassBook>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPassBook[]) => (this.passbooks = concatRes));
          }
        });
    });
  }

  updateForm(wallet: IWallet): void {
    this.editForm.patchValue({
      id: wallet.id,
      balance: wallet.balance,
      owenerId: wallet.owenerId,
      passbookId: wallet.passbookId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wallet = this.createFromForm();
    if (wallet.id !== undefined) {
      this.subscribeToSaveResponse(this.walletService.update(wallet));
    } else {
      this.subscribeToSaveResponse(this.walletService.create(wallet));
    }
  }

  private createFromForm(): IWallet {
    return {
      ...new Wallet(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      owenerId: this.editForm.get(['owenerId'])!.value,
      passbookId: this.editForm.get(['passbookId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWallet>>): void {
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

  trackById(index: number, item: IPassBook): any {
    return item.id;
  }
}
