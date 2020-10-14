import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NovopayassignmentTestModule } from '../../../test.module';
import { PassBookDetailComponent } from 'app/entities/pass-book/pass-book-detail.component';
import { PassBook } from 'app/shared/model/pass-book.model';

describe('Component Tests', () => {
  describe('PassBook Management Detail Component', () => {
    let comp: PassBookDetailComponent;
    let fixture: ComponentFixture<PassBookDetailComponent>;
    const route = ({ data: of({ passBook: new PassBook(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NovopayassignmentTestModule],
        declarations: [PassBookDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PassBookDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PassBookDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load passBook on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.passBook).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
