import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPassBook, PassBook } from 'app/shared/model/pass-book.model';
import { PassBookService } from './pass-book.service';

@Component({
  selector: 'jhi-pass-book-update',
  templateUrl: './pass-book-update.component.html',
})
export class PassBookUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected passBookService: PassBookService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ passBook }) => {
      this.updateForm(passBook);
    });
  }

  updateForm(passBook: IPassBook): void {
    this.editForm.patchValue({
      id: passBook.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const passBook = this.createFromForm();
    if (passBook.id !== undefined) {
      this.subscribeToSaveResponse(this.passBookService.update(passBook));
    } else {
      this.subscribeToSaveResponse(this.passBookService.create(passBook));
    }
  }

  private createFromForm(): IPassBook {
    return {
      ...new PassBook(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPassBook>>): void {
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
}
