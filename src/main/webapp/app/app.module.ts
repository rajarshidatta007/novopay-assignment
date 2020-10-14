import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { NovopayassignmentSharedModule } from 'app/shared/shared.module';
import { NovopayassignmentCoreModule } from 'app/core/core.module';
import { NovopayassignmentAppRoutingModule } from './app-routing.module';
import { NovopayassignmentHomeModule } from './home/home.module';
import { NovopayassignmentEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    NovopayassignmentSharedModule,
    NovopayassignmentCoreModule,
    NovopayassignmentHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    NovopayassignmentEntityModule,
    NovopayassignmentAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class NovopayassignmentAppModule {}
