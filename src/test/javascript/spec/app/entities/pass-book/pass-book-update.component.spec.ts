import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NovopayassignmentTestModule } from '../../../test.module';
import { PassBookUpdateComponent } from 'app/entities/pass-book/pass-book-update.component';
import { PassBookService } from 'app/entities/pass-book/pass-book.service';
import { PassBook } from 'app/shared/model/pass-book.model';

describe('Component Tests', () => {
  describe('PassBook Management Update Component', () => {
    let comp: PassBookUpdateComponent;
    let fixture: ComponentFixture<PassBookUpdateComponent>;
    let service: PassBookService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NovopayassignmentTestModule],
        declarations: [PassBookUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PassBookUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PassBookUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PassBookService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PassBook(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PassBook();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
