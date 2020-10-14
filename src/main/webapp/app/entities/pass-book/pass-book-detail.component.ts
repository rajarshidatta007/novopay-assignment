import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPassBook } from 'app/shared/model/pass-book.model';

@Component({
  selector: 'jhi-pass-book-detail',
  templateUrl: './pass-book-detail.component.html',
})
export class PassBookDetailComponent implements OnInit {
  passBook: IPassBook | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ passBook }) => (this.passBook = passBook));
  }

  previousState(): void {
    window.history.back();
  }
}
