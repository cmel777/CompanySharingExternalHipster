import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiPaginationUtil, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal, DataCollection, DataCollectionService, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-data-collection-mgmt',
    templateUrl: './data-collection-management.component.html',
    providers: [DataCollectionService]
})
export class DataCollectionMgmtComponent implements OnInit, OnDestroy {

    currentAccount: any;
    dataCollections: DataCollection[];
    error: any;
    success: any;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private dataCollectionService: DataCollectionService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll();
            this.registerChangeInDataCollections();
        });
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    registerChangeInDataCollections() {
        this.eventManager.subscribe('userListModification', (response) => this.loadAll());
    }

    setActive(dataCollection, isActivated) {
        dataCollection.activated = isActivated;

        this.dataCollectionService.update(dataCollection).subscribe(
            (response) => {
                if (response.status === 200) {
                    this.error = null;
                    this.success = 'OK';
                    this.loadAll();
                } else {
                    this.success = null;
                    this.error = 'ERROR';
                }
            });
    }

    loadAll() {
        this.dataCollectionService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trackIdentity(index, item: DataCollection) {
        return item.id;
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/data-collection-management'], {
            queryParams: {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    checkbox(dataCollection: DataCollection) {
        // dataCollection.enabled = !dataCollection.enabled;
        this.dataCollectionService.update(dataCollection).subscribe(
            (response) => {
                if (response.status === 200) {
                    this.error = null;
                    this.success = 'OK';
                    // this.loadAll(); // - We dont need to reload all the users do we?
                } else {
                    this.success = null;
                    this.error = 'ERROR';
                    // dataCollection.enabled = !dataCollection.enabled;
                    // TODO display the error message outlet.
                    this.onError( {error: this.error, message: 'Something went wrong - get this from i8n'} );
                }
            });
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.dataCollections = data;
    }

    private onError(error) {
        console.log( 'Calling the alert service with error:' );
        console.log(error);
        this.alertService.error(error.error, error.message, null);
    }

    clickMe() {
        this.alertService.error('404', {}, null);
        this.alertService.success('Success', {}, null);
    }
}
