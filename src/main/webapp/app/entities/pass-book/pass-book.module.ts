import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NovopayassignmentSharedModule } from 'app/shared/shared.module';
import { PassBookComponent } from './pass-book.component';
import { PassBookDetailComponent } from './pass-book-detail.component';
import { PassBookUpdateComponent } from './pass-book-update.component';
import { PassBookDeleteDialogComponent } from './pass-book-delete-dialog.component';
import { passBookRoute } from './pass-book.route';

@NgModule({
  imports: [NovopayassignmentSharedModule, RouterModule.forChild(passBookRoute)],
  declarations: [PassBookComponent, PassBookDetailComponent, PassBookUpdateComponent, PassBookDeleteDialogComponent],
  entryComponents: [PassBookDeleteDialogComponent],
})
export class NovopayassignmentPassBookModule {}
